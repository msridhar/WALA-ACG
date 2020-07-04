# WALA-ACG

This is a simple driver for running WALA's approximate call graph construction for JavaScript, as described in [this research paper](https://manu.sridharan.net/files/ICSE-2013-Approximate.pdf):

> Asger Feldthaus, Max Schäfer, Manu Sridharan, Julian Dolby, and Frank Tip. Efficient construction of approximate call graphs for JavaScript IDE services. In ICSE, 2013.

To run the builder, just do:
```bash
./gradlew run --args="path_to_js_or_html_file"
```

E.g., `./gradlew run --args="foo.js"` should build a call graph for `foo.js`.  Right now the call graph stats are printed and the call graph is printed as JSON.
