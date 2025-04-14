import time
import requests

PORT = 8080

headers = {
    "Content-Type": "application/json"
}

data = {
    "cheapestmeal/": None,
    "addorder/":
        {
            "address": "Brazil",
            "orderItems": {
                "4237681a-441f-47fc-a747-8e0169bacea1": 2,
                "cfd1601f-29a0-485d-8d21-7607ec0340c8": 3
            }
        }
}


def test_rest(url: str):
    start = time.perf_counter_ns()
    try:
        for path in data:
            if data[path] is not None:
                response = requests.post(url + 'rest/' + path, json=data[path], headers=headers)
            else:
                response = requests.get(url + 'rest/' + path, headers=headers)
        return time.perf_counter_ns() - start, response.status_code == 200
    except requests.RequestException:
        return time.perf_counter_ns() - start, False


def test_restrpc(url: str):
    start = time.perf_counter_ns()
    try:
        for path in data:
            if data[path] is not None:
                response = requests.post(url + 'restrpc/' + path, json=data[path], headers=headers)
            else:
                response = requests.get(url + 'restrpc/' + path, headers=headers)

        return time.perf_counter_ns() - start, response.status_code == 200
    except requests.RequestException:
        return time.perf_counter_ns() - start, False
