import math

text = int(java{text})

vector = []

for c in text * 200:
    vector.append(ord(c) % 50)

total = 0

for i in range(len(vector)):
    val = vector[i]
    total += math.sqrt(val * val + i)

average = total / len(vector)

result = f"vector_avg={average}"