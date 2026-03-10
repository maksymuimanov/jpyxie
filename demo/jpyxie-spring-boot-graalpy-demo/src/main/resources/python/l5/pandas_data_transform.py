import pandas as pd
import numpy as np

integer = java{integer}

# generate synthetic data
n = 10000 + (integer % 5000)
df = pd.DataFrame({
    "A": np.random.randn(n),
    "B": np.random.randn(n),
    "C": np.random.choice(range(10), size=n)
})

# group and aggregate
agg = df.groupby("C").agg({"A": ["mean","std"], "B": ["min","max"]})
agg_list = agg.values.tolist()

result = f"groups={len(agg)},agg_sample={agg_list[:3]}"