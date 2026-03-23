text = java{text}

words = text.split()

lengths = []
for w in words:
    lengths.append(len(w))

total = sum(lengths)
average = total / len(lengths) if lengths else 0

result = "words={0},avg_len={1}".format(len(words), average)