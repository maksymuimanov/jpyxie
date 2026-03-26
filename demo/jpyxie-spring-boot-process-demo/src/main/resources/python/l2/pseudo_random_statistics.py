integer = java{integer}

numbers = []

seed = integer + 1

for i in range(10000):
    seed = (seed * 1103515245 + 12345) % 2**31
    numbers.append(seed % 1000)

numbers.sort()

median = numbers[len(numbers)//2]
average = sum(numbers) / len(numbers)

result = "median={0},avg={1}".format(median, average)