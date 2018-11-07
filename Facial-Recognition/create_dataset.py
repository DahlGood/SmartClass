import cv2
from time import sleep


NUM_OF_IMAGES = 500
cap = cv2.VideoCapture(0)
count = 0
while(True):
	# capture frame by frame
	ret, frame = cap.read()
	gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

	cv2.imshow('frame', gray)
	if cv2.waitKey(20) & 0xFF == ord('q'):
		break

	if count != NUM_OF_IMAGES:
		print(count)
		img_item = "training-data/john/" + str(count) + ".png"
		cv2.imwrite(img_item, gray)
		count += 1
	else:
		break

cap.release()
cv2.destroyAllWindows()