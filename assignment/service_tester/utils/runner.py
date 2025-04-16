import time
import concurrent.futures
import csv


def run_test(func, args, clients: int, req_per_sec: int, length: int, result_file: str):
    results = []

    def wrapper():
        elapsed, success = func(*args)
        results.append((elapsed, success))

    futures = []
    executors = [concurrent.futures.ThreadPoolExecutor(max_workers=req_per_sec*2) for _ in range(clients)]
    for _ in range(length):
        for executor in executors:
            futures += [executor.submit(wrapper) for _ in range(req_per_sec)]
        time.sleep(1)
    concurrent.futures.wait(futures)

    if result_file is not None:
        with open(result_file, 'w') as f:
            writer = csv.writer(f)
            writer.writerow(["response_time", "success"])
            writer.writerows(results)
    else:
        print(f'Results:\n{results}')
