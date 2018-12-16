import cv2
import matplotlib.pyplot as plt
from imutils.video import VideoStream
import face_recognition
import pickle
import argparse
import datetime
import imutils
import time


def count_faces(f_cascade, colored_img, scaleFactor = 1.1):
	img_copy = colored_img.copy()
	gray = cv2.cvtColor(img_copy, cv2.COLOR_BGR2GRAY)

	return len(f_cascade.detectMultiScale(gray, scaleFactor=scaleFactor, minNeighbors=8))

def detect_faces_in_frame(frame, cascade):
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        
        rects = cascade.detectMultiScale(gray, scaleFactor=1.1, minNeighbors=5, minSize=(30,30))

        boxes = [(y, x+w, y+h, x) for (x,y,w,h) in rects]

        encodings = face_recognition.face_encodings(rgb, boxes)
        names = []
        present = []
        
        for encoding in encodings:
                matches = face_recognition.compare_faces(data["encodings"],encoding)
                name = "Unknown"
        
                # check if we founds a match
                if True in matches:
                        matchedIdxs = [i for (i,b) in enumerate(matches) if b]
                        counts = {}

                        for i in matchedIdxs:
                                name = data["names"][i]
                                counts[name] = counts.get(name, 0) + 1
                                present.append(name)
                        name = max(counts, key=counts.get)

                names.append(name)
        for ((top, right, bottom, left), name) in zip(boxes, names):
                cv2.rectangle(frame, (left, top), (right, bottom), (0,255,0),2)
                y = top - 15 if top - 15 > 15 else top + 15
                cv2.putText(frame, name, (left, y), cv2.FONT_HERSHEY_SIMPLEX, 0.75, (0,255,0), 2)
                
        cv2.imshow("Frame", frame)
        return set(present)

def roll_call(present, data):
        attendance = {}
        
        all_students = set(data['names'])
        for i in all_students:
                if i in present:
                        attendance[i] = 'Present'
                else:
                        attendance[i] = 'Absent'
        return(attendance)

        
ap = argparse.ArgumentParser()
ap.add_argument("-c", "--cascade", required=True,
	help = "path to where the face cascade resides")
ap.add_argument("-e", "--encodings", required=True,
	help="path to serialized db of facial encodings")
args = vars(ap.parse_args())

# load the known faces and embeddings along with OpenCV's Haar
# cascade for face detection
print("[INFO] loading encodings + face detector...")
data = pickle.loads(open(args["encodings"], "rb").read())
detector = cv2.CascadeClassifier(args["cascade"])

vs = VideoStream(src=0).start()
time.sleep(2.0)

while True:
	frame = vs.read()

	gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
	faces = detector.detectMultiScale(gray, scaleFactor=1.1, minNeighbors=8)
	text = "Faces detected: " + str(len(faces))
	cv2.rectangle(frame, (20,25), (260, 60), (0, 0, 0), -1)
	cv2.putText(frame, text, (30,50), cv2.FONT_HERSHEY_SIMPLEX, .75, (255,255,255), 1)

	for (x,y,w,h) in faces:
 		cv2.rectangle(frame, (x,y), (x+w, y+h), (0, 255, 0), 2)

	cv2.imshow("Classroom", frame)

	key = cv2.waitKey(1) & 0xFF

	if key == ord("q"):
		break
	if key == ord("\r"): # enter key
                print('detecting faces')
                present = detect_faces_in_frame(frame, detector)
                print(present)
                print(roll_call(present,data))
                
vs.stop()
cv2.destroyAllWindows()
