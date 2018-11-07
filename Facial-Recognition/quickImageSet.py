from requests import exceptions
import argparse
import requests
import cv2
import os

key1 = '5139565a7b2f4728881310d308069346'
key2 = 'c004718a89584384a8dc1cf814d29b0f'

# construct the argument parser and parse the arguments
ap = argparse.ArgumentParser()
ap.add_argument("-q", "--query", required=True,
	help="search query to search Bing Image API for")
ap.add_argument("-o", "--output", required=True,
	help="path to output directory of images")
args = vars(ap.parse_args())

# set your Microsoft Cognitive Services API key along with (1) the
# maximum number of results for a given search and (2) the group size
# for results (maximum of 50 per request)
API_KEY = key1
MAX_RESULTS = 50
GROUP_SIZE = 50

# set the endpoint API URL
URL = "https://api.cognitive.microsoft.com/bing/v7.0/images/search"

# when attempting to download images from the web both the Python
# programming language and the requests library have a number of
# exceptions that can be thrown so let's build a list of them now
# so we can filter on them
EXCEPTIONS = set([IOError, FileNotFoundError, 
	exceptions.RequestException, exceptions.HTTPError,
	exceptions.ConnectionError, exceptions.Timeout])


# store the search term in a convenience variable then set the
# headers and search parameters
term = args["query"]
headers = {"Ocp-Apim-Subscription-Key" : API_KEY}
params = {"q": term, "offset": 0, "count": GROUP_SIZE}

# make the search
print(f"[INFO] searching Bing API for '{term}'")
search = requests.get(URL, headers=headers, params=params)
search.raise_for_status()

# grab the results from the search, including the total number of
# estimated results returned by the Bing API]
results = search.json()
estNumResults = min(results["totalEstimatedMatches"], MAX_RESULTS)
print(f"[INFO] {estNumResults} total results for '{term}'")

# initialize the total number of images downloaded thus far
total = 0

# loop over the estimated number of results in 'GROUP_SIZE' groups
for offset in range(0, estNumResults, GROUP_SIZE):
	# update the search parameters using the current offset, then
	# make the request to fetch the results
	print(f"[INFO] making request for group {offset}-{offset+GROUP_SIZE} of {estNumResults}...")
	params["offset"] = offset
	search = requests.get(URL,headers=headers, params=params)
	search.raise_for_status()
	results = search.json()
	print(f"[INFO] saving images for group {offset}-{offset+GROUP_SIZE} of {estNumResults}...")

	# loop over the results
	for v in results["value"]:
		contentUrl = v["contentUrl"]
		# try to download the image
		try:
			#make a request to download the image
			print(f"[INFO] fetching: {contentUrl}")
			r = requests.get(v["contentUrl"], timeout=30)

			# build the path to the output image
			ext = v["contentUrl"][v["contentUrl"].rfind("."):]
			p = os.path.sep.join([args["output"], f"{str(total).zfill(8)}{ext}".format(
				str(total).zfill(8), ext)])

			# write the image to disk
			f = open(p, "wb")
			f.write(r.content)
			f.close()

		# catch any errors that would not enable us to download the image
		except Exception as e:
			# check to see if out exception is in out list of
			# exceptions to check for
			if type(e) in EXCEPTIONS:
				print(f"[INFO] skipping: {contentUrl}")
				continue


		# try to load the image from disk
		image = cv2.imread(p)

		# if the image is 'None' then we could not properly load the
		# image from disk (so it should be ignored)
		if image is None:
			print(f"[INFO] deleting: {p}")
			os.remove(p)
			continue

		#update the counter
		total += 1




























