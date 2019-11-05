definition(
    name: "[013] Phát hiện cháy thì báo động",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Kịch bản an toàn cho ngôi nhà",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")
//Test:OK
preferences {
  section("Tùy chọn thông số kịch bản")
  {
  input "smokeH", "capability.smokeDetector", title: "Khi phát hiện cháy tại cảm biến"
  input("alamH","capability.alarm",title:"Thì báo động sau sẽ được bật")
  input name:"txtmobi",type:"text", title:"Khi không có internet gửi SMS vào số điện thoại này :+84SĐT",defaultValue:" "
  }
  }
def init()
{
	subscribe(smokeH, "smoke", smoke_H)
  	subscribe(alamH, "alarm", alam_H)
}

def installed() 
{
	init()
}

def updated() 
{
	unschedule()
   	init()	
}

def smoke_H(evt) {
  if("detected" == evt.value) 
  {
  	alamH.siren()
  	sendPush("Phát hiện khói/cháy tại: ${evt.displayName}")
    sendSms({$txtmobi},"Phát hiện khói/cháy tại: ${evt.displayName}. Hãy kiểm tra lại hoặc xem qua camera")
  }
   if("tested" == evt.value) 
  {
  	alamH.siren()
  	sendPush("[Đang sử dụng nút Test]")
  
  }
}