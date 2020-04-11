const express = require("express");
const proxy = require("http-proxy-middleware");

const app = express();
let url = "http://localhost:8080";
if (process.argv[2]) {
    url = process.argv[2];
}
app.use(
    proxy("/api", {
        target: url,
        changeOrigin: true,
        onProxyReq: function onProxyReq(proxyReq, req, res) {
            // stub
        }
    }),
    proxy("/", {
        target: "http://localhost:1200/#/",
        changeOrigin: true
    })
);
app.listen(1234);
