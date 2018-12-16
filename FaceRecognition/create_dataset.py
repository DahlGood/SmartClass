import cv2
import os
import argparse
from time import sleep

ap = argparse.ArgumentParser()
ap.add_argument("-n", "--name", required=True, help="the name of the student being added to the dataset")
args = vars(ap.parse_args())

NUM_OF_SAMPLES = 10

video = cv2.VideoCapture(0)

face_detector = cv2.CascadeClassifier('haarcascades/haarcascade_frontalface_default.xml')
path = 'dataset/' + args['name']

# if directory exists, delete all images so new images can be added
if os.path.isdir(path):
	for i in os.listdir(path):
		os.remove(path + '/' + i)
# else create the directory
else:
	os.mkdir(path)

if len(os.listdir(path)) == 0:
	print('[INFO] Directory is empty')
else:
	print('[ERROR] Something went wrong, not all images removed')

print("[INFO] Saving images to " + path)
print("[INFO] Target number of images is " + str(NUM_OF_SAMPLES))
input("Press enter to capture training images...")
sleep(2)

while len(os.listdir(path)) < NUM_OF_SAMPLES:
	i = len(os.listdir(path))
	ret, img = video.read()
	gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
	faces = face_detector.detectMultiScale(gray, 1.3, 5)

	# drawing rectangle around
	for (x,y,w,h) in faces:
		text = 'Capturing image ' + str(len(os.listdir(path)))
		cv2.rectangle(img, (x,y), (x+w, y+h), (0, 135, 255), 2)
		cv2.putText(img, text, (x-10,y-10), cv2.FONT_HERSHEY_SIMPLEX, 1, (0,135,255),2)
		#save to correct directory
		cv2.imwrite(path + '/' + str(i) + '.jpg', gray[y:y+h,x:x+w])
		cv2.imshow('image', img)

	k = cv2.waitKey(1) & 0xFF

	if k == ord('q'):
		break

video.release()
cv2.destroyAllWindows()

if len(os.listdir(path)) == NUM_OF_SAMPLES:
	print('\n[INFO] ' +str(len(os.listdir(path))) + '/' + str(NUM_OF_SAMPLES) + ' images saved to ' + path)
else:
	print('\n[ERROR] Only ' + str(len(os.listdir(path))) + '/' + str(NUM_OF_SAMPLES) + ' saved to ' + path)
