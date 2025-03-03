// app.js
App({
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

  },
  globalData: {
    userIdentity: null ,
    userSchool: null ,
    userCode: null ,
    userName: null ,
    userSex: null ,
    userTele: null ,
    userAvatar: null ,
    drivermarks:null,
    drivermarksla:[null],
    drivermarkslo:[null],
    drivermarkssc:[null],

  },
})
