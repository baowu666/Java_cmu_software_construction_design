with open("stopWords.txt", 'r') as f:
	words = f.read()
	newWords = []
	for w in words.splitlines():
		if not w.isspace():
			newWords += [w]
	contents = " ".join(newWords)
	print contents
with open("temp.txt", 'w') as fout:
	fout.write(contents)
