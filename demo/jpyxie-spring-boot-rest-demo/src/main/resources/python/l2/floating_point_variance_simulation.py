float_value = java{float}

values = []

for i in range(10000):
    val = (float_value + i) * 1.0001
    val = val / (1.00005 + (i % 5))
    values.append(val)

mean = sum(values) / len(values)

variance = 0
for v in values:
    variance += (v - mean) ** 2

variance /= len(values)

result = f"mean={mean},var={variance}"