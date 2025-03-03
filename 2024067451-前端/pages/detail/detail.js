// pages/detail/detail.js
var util = require('../../utils/util.js');
const app=getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    inputValue: '', // 输入框的值
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var did = options.did;
    console.log('接收到的司机ID：', did);
    wx.request({
      // 调用后端接口
      url: 'http://43.138.116.211:5467/detail',
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      data:{
        did:did,
      },
      success: (res) => {
        console.log(res);
        // 数据转换
        var json = res.data;
        console.log(json);
        console.log(json.userName);
       var avatar=json.userAvatar
        var name=json.userName
        var sex=json.userSex
        var tele=json.userTelephone
        var people=json.userAgree
        this.setData({
          did:did,
          avatar:avatar,
          name:name,
          sex:sex,
          tele:tele,
          people:people
        });
       
      }
    });
    
    this.refresh(did);
  },
  refresh:function(e) {
    console.log(e)
    var did=e;
    wx.request({
      // 调用后端接口
      url: 'http://43.138.116.211:5467/comment',
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      data:{
        did:did,
      },
      success: (res) => {
        console.log(res);
        var json = res.data; // 将返回的数据转换为JSON格式
        console.log(json)
        if(json !== ''){
          this.setData({
            commentData: json
          });
        }
        
      
      }
    });
  },

 add: function(e) {
   console.log(this.data.people)
   var currentPeople = this.data.people; // 获取当前的 people 值
   currentPeople ++; // 进行加法运算
   this.setData({ people: currentPeople}); // 更新数据
   var did=this.data.did;
   console.log(did);
   wx.request({
    // 调用后端接口
    url: 'http://43.138.116.211:5467/add',
    method: 'POST',
    header: {
      'content-type': 'application/json'
    },
    data:{
      did:did,
      people: currentPeople, // 请求体中封装code
    },
    success: (res) => {
      console.log(res);
      
    }
  });
},
handleInput(e) {
  this.setData({
    inputValue: e.detail.value
  });
},
sendcomment() {
   // 调用函数时，传入new Date()参数，返回值是日期和时间
   let time = util.formatTime(new Date());
   console.log("发送时间",time)

   var did=this.data.did;
   console.log(did)
  var avatar=app.globalData.userAvatar;
  var name=app.globalData.userName;
  var Identity=app.globalData.userIdentity;
  var content=this.data.inputValue;
  if (!content) {
    wx.showModal({
      title: '提示',
      content: '内容不能为空，请输入内容后再发送',
      showCancel: false  // 不显示取消按钮
    });
    return; // 退出函数，不执行后续代码
  }  
  wx.request({
    // 调用后端接口
    url: 'http://43.138.116.211:5467/upcomment',
    method: 'POST',
    header: {
      'content-type': 'application/json'
    },
    data:{
      did:did,
    name:name,
    avatar:avatar,
    Identity:Identity,
    content: content,
    time:time
    },
    success: (res) => {
      console.log(res);
      // 数据转换
      this.setData({
        inputValue: ''
      });
      console.log(did);
      this.refresh(did);
      wx.showToast({
        title: '发表成功！',
        icon: 'none', // 设置提示窗口的图标，这里使用无图标
        duration: 1000 // 设置提示窗口显示时间为 5 秒
      });
    }
  });

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