// 引入http模块
var http = require('http');
// 创建http服务
var server = http.createServer(function (req, res) {
  console.log('Lee welcome to node.js');
});
// 监听端口号
server.listen(8888, function () {
  console.log('start server');
});
