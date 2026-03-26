integer = java{integer}
float_value = java{float}

values = []
base = integer + 1

for i in range(50):
    val = (base * i) + float_value
    values.append(val)

avg = sum(values) / len(values)
minimum = min(values)
maximum = max(values)

result = f"avg={avg},min={minimum},max={maximum}"
print(result)