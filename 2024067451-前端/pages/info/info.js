// pages/info/info.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    BtnTxt:'保存',
    BtnBgBgColor:'#d1e9c5',//初始时背景颜色，点击后变为灰色
    btnLoading:false,//控制按钮点击后是否显示loading效果
    disabled:false,//保存中，按钮不能点击
    username:'',
    usersex:'',
    usertele:'',
    nameTxt:getApp().globalData.userName,
    sexTxt:getApp().globalData.userSex,
    teleTxt:getApp().globalData.userTele,
    jin:false,


  },
 

usernameInput:function(e){
  var val = e.detail.value;
  console.log(val)
    this.setData({
      username:val
    })
},

nameInput:function(e){
  var val = e.detail.value;
  console.log(val)
    this.setData({
      usersex:val
    })
},

passwordInput:function(e){
  var val = e.detail.value;
  console.log(val)
    this.setData({
      usertele:val
    })
},

//同登录类似
post:function(){
  if(this.data.username===""){
    wx.showModal({
      title: '提示', 
      content: '用户名不能为空',
      cancelText:'取消',
      confirmText:'确定', 
      confirmColor:'#000000', 
      cancelColor:'#576b95',
      success (res) { 
        if (res.confirm) {
          console.log('用户点击确认') 
        } else if (res.cancel) { 
          console.log('用户点击取消') 
        }
      } 
    })
  }else{
    this.setData({
      loginBtnTxt:"保存中",
      disabled: !this.data.disabled,
      loginBtnBgBgColor:"#999",
      btnLoading:!this.data.btnLoading
    });
    var role=getApp().globalData.userIdentity;
    var school=getApp().globalData.userSchool;
    wx.request({
      url: 'http://43.138.116.211:5467/info',
      method:"POST",
      data:{
        role:role,
        school:school,
        username:this.data.username,
        usersex:this.data.usersex,
        usertele:this.data.usertele
      },
      success:function(res){
          console.log(res);
          if(res.data){
            console.log("保存成功");
            // 在 info 页面中跳转到 mine 页面，并在跳转前设置标记
            wx.setStorageSync('refreshMinePage', true);
            wx.switchTab({
              url: '/pages/mine/mine',
            });
          }
          else{
            console.log("保存失败");
          }
      },
      
    })

  }
 
},
  

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var avatar=app.globalData.userAvatar
    this.setData({
      avatar:avatar
    })

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})