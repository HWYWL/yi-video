const app = getApp()

Page({
    data: {

    },

    doRegist: function(e){
      var fromObject = e.detail.value;
      var username = fromObject.username;
      var password = fromObject.password;

      //  前端校验
      if(username.length == 0 || password.length == 0){
        wx.showToast({
          title: '用户名或密码不能为空!',
          icon: 'none',
          duration: 2000
        })
      }else {
        var serverUrl = app.serverUrl;
        wx.showLoading({
          title: '请等待。。。',
        });

        // 请求后端注册接口
        wx.request({
          url: serverUrl + '/regist',
          method: 'POST',
          data: {
            username: username,
            password: password
          },
          header: {
            'content-type': 'application/json' // 默认值
          },
          success: function (res) {
            wx.hideLoading();
            var status = res.data.status;
            if(status == 200){
              wx.showToast({
                title: '用户注册成功！',
                icon: 'none',
                duration: 2000
              }),

              // 设置全局变量 用户数据
              // app.userInfo = res.data.data;
              // 将用户信息放入本地缓存
              app.setGlobalUserInfo(res.data.data);
            }else if(status == 500){
              wx.showToast({
                title: res.data.msg,
                icon: 'none',
                duration: 2000
              })
            }

            wx.redirectTo({
              url: '../userLogin/login',
            })
          }
        })
      }
    },

    goLoginPage: function () {
      wx.redirectTo({
        url: '../userLogin/login',
      })
    }
})