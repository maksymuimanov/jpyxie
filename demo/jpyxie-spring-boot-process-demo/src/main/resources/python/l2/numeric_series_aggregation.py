integer = java{integer}
text = java{text}

def generate_series(n):
    data = []
    value = 1
    for i in range(n):
        value = (value * 3 + i) % 100000
        data.append(value)
    return data

series = generate_series(5000 + integer * 200)

total = sum(series)
maximum = max(series)
minimum = min(series)

text_score = sum(ord(c) for c in text)

result = f"sum={total},max={maximum},min={minimum},text_score={text_score}"
print(result)