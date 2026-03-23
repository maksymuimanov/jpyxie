integer = java{integer}

def simulate():
    value = 1.0

    for i in range(200000):
        value = (value * 1.000001) + (i % 17)
        value = value / 1.0000001

    return value

runs = 3 + (integer % 3)

results = []
for _ in range(runs):
    results.append(simulate())

avg = sum(results) / len(results)

result = "simulation_avg={0}".format(avg)