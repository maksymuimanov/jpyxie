import math

integer = int(java{integer})

points = []

for i in range(20000):
    x = math.sin(i + integer)
    y = math.cos(i * 0.5)
    points.append((x, y))

distance_sum = 0

for i in range(1, len(points)):
    x1, y1 = points[i-1]
    x2, y2 = points[i]

    dx = x2 - x1
    dy = y2 - y1

    distance_sum += math.sqrt(dx*dx + dy*dy)

result = f"path_length={distance_sum}"