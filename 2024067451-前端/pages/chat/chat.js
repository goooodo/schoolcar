// pages/chat/chat.js
var util = require('../../utils/util.js');
const app=getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    InputBottom: 0,
    messages: [], // 存放聊天消息的数组
    inputValue: '', // 输入框的值
    toView: '', // 用于实现自动滚动

  },
  handleInput(e) {
    this.setData({
      inputValue: e.detail.value
    });
  },
  createWebSocketConnection(connectionID) {
    const socketTask = wx.connectSocket({
      url: "ws://" + "43.138.116.211" + ":5467/websocket?connectionId="+connectionID+"&userId=" + app.globalData.userCode, // WebSocket 服务器地址
      success: () => {
        console.log('WebSocket 连接已打开');
        this.setData({
          socketOpen: true
        });
        
      },
      
      fail: (error) => {
        console.error('WebSocket 连接打开失败：', error);
      }
    });

    // 监听 WebSocket 连接打开事件
    socketTask.onOpen(() => {
      console.log('WebSocket 连接打开');
    });

    // 监听 WebSocket 接收到服务器的消息事件
    socketTask.onMessage((res) => {
      console.log('chat接收到消息：', res);
      // 在这里处理接收到的消息
      let data = JSON.parse(res.data);  // 获取消息内容
      console.log(data);
      var id=data.id;
      var sender=data.name;
      var avatar=data.avatar;
      var school=data.school;
     
      if (data.Identity === 0) {
        var Identity="司机";
      } else if (data.Identity === "1") {
        var Identity="乘客";
      } 
      console.log(Identity);
      var content=data.content;
      var time=data.time;
      console.log(sender);
      console.log(content);
      let cla=0;
      if(id===app.globalData.userCode && school===app.globalData.userSchool){
        cla=1;
      }
      console.log("样式选择",cla);
      

      const newMessage = {
        avatar:avatar,
        sender: sender+"  身份："+Identity,
        content: content,
        time:" "+time,
        cla:cla
      };
      if(school===app.globalData.userSchool){
        const messages = this.data.messages.concat(newMessage);
        this.setData({
          messages,
          inputValue: '',
          toView: `item${messages.length - 1}`
        });
      }
     
    
      
    });

    // 监听 WebSocket 连接关闭事件
    socketTask.onClose((res) => {
      console.log('map WebSocket 连接关闭：', res);
      this.setData({
        socketOpen: false
      });
    });

    // 将 WebSocket 任务保存到 data 中，方便后续操作
    this.setData({
      socketTask: socketTask
    });
  },

  sendMessage() {
    const socketTask = this.data.socketTask;
     // 调用函数时，传入new Date()参数，返回值是日期和时间
     let time = util.formatTime(new Date());
     console.log("发送时间",time)
 
    
    var id=app.globalData.userCode;
    var avatar=app.globalData.userAvatar;
    var name=app.globalData.userName;
    var Identity=app.globalData.userIdentity;
    var school=app.globalData.userSchool;
    var content=this.data.inputValue;
    var message = {
      id:id,
      name:name,
      avatar:avatar,
      Identity:Identity,
      content: content,
      time:time,
      school:school
    };
    if (!content) {
      wx.showModal({
        title: '提示',
        content: '消息内容不能为空，请输入内容后再发送',
        showCancel: false  // 不显示取消按钮
      });
      return; // 退出函数，不执行后续代码
    }  
  if (socketTask && this.data.socketOpen) {
    socketTask.send({
      data:JSON.stringify(message),
      success: () => {
        console.log('chat 消息发送成功');
      },
      fail: (error) => {
        console.error('chat消息发送失败：', error);
      }
    });
    
  } else {
    console.error('map WebSocket 连接未打开或 socketTask 为空');
  }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad() {
    this.createWebSocketConnection("connection3");

  },
  
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
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
    this.closeWebSocketConnection();

  },
  // 关闭 WebSocket 连接
  closeWebSocketConnection() {
    const socketTask = this.data.socketTask;
    if (socketTask && this.data.socketOpen) {
      socketTask.close({
        success: () => {
          console.log('map WebSocket 连接已关闭');
          this.setData({
            socketOpen: false
          });
        },
        fail: (error) => {
          console.error('map WebSocket 连接关闭失败：', error);
          this.setData({
            socketOpen: false
          });
        }
      });
    }
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