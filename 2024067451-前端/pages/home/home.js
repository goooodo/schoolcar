Page({
  data: {
    msgList: [{
      title: "认真学习宣传贯彻党的二十大精神"
    }, {
      title: "提高警惕，防范诈骗"
    }],
    userData: [], // 用于存储返回的数据
  },
  
  onLoad() {
    var userschool= getApp().globalData.userSchool;
    var that = this;
    wx.request({
      // 调用后端接口
      url: 'http://43.138.116.211:5467/news',
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      data:{
        school:userschool,
      },
      success: (res) => {
        console.log(res);
        // 数据转换
        var json = res.data;
        console.log(json);
        that.setData({
          userData: json
        });
        
      }
    });
  },
  // 点击事件处理函数
detail: function(event) {
  // 获取点击司机信息的数据索引
  var index = event.currentTarget.dataset.index;
  console.log(index);
  // 获取点击的司机信息
  var newsInfo = this.data.userData[index];
  var title=newsInfo.theam;
  var photo=newsInfo.photo;
  var context=newsInfo.context;
  console.log(title);
  // 输出点击的司机信息
  console.log('点击的司机信息：', newsInfo);
  wx.navigateTo({
    url: '../../pages/newsdetail/newsdetail?title=' + title +"&photo=" + photo+"&context=" +context 
  });
  // 进行其他操作，比如跳转到详情页等
},
  onUnload(){
    
  },
})
