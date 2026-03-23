text = java{text}

freq = {}

for c in text:
    freq[c] = freq.get(c, 0) + 1

sorted_items = sorted(freq.items(), key=lambda x: x[1], reverse=True)

top_chars = []
for k, v in sorted_items[:5]:
    top_chars.append("{0}:{1}".format(k, v))

joined = ",".join(top_chars)

result = "top={0}".format(joined)