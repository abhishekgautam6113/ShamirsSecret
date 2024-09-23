#include <iostream>
#include <vector>
#include <nlohmann/json.hpp>
#include <fstream>
#include <sstream>
#include <cmath>

using json = nlohmann::json;
using namespace std;

int decodeValue(const string& value, int base) {
    return stoi(value, nullptr, base);  // Converts string to integer with given base
}

int calculate_Y(int x, vector<int>& poly) {
    int y = 0, temp = 1;
    for (auto coeff : poly) {
        y += coeff * temp;
        temp *= x;
    }
    return y;
}

void processTestCase(const string& fileName) {
    // Load JSON data from file
    ifstream file(fileName);
    json jsonData;
    file >> jsonData;

    int n = jsonData["keys"]["n"];
    int k = jsonData["keys"]["k"];

    // Collect points (x, y)
    vector<pair<int, int>> points;
    for (auto it = jsonData.begin(); it != jsonData.end(); ++it) {
        if (it.key() != "keys") {
            int x = stoi(it.key());  // Convert the key to int (x-value)
            int base = stoi(it.value()["base"].get<string>());
            string value = it.value()["value"];
            int y = decodeValue(value, base);  // Decode y-value using base
            points.emplace_back(x, y);
        }
    }

    // Lagrange interpolation to find the constant term (c)
    double c = 0;
    for (int i = 0; i < points.size(); ++i) {
        double term = points[i].second;
        for (int j = 0; j < points.size(); ++j) {
            if (i != j) {
                term *= (0 - points[j].first) / (double)(points[i].first - points[j].first);
            }
        }
        c += term;
    }

    cout << "The constant term 'c' is: " << c << endl;
}

int main() {
    processTestCase("testcase1.json");  // Adjust the file path accordingly
    processTestCase("testcase2.json");
    return 0;
}
