
students = {
	"john": {
		'is_here': 0
		},
	"elvis": {
		'is_here': 0
		}		
	}

def mark_present(labels, id_):
	students[labels[id_]]['is_here'] = 1

def print_attendance(d):
	for i in d:
		if d[i]['is_here'] == 1:
			print(i + ': PRESENT')
		else:
			print(i + ': ABSENT')


if __name__ == "__main__":
	print_attendance(students)