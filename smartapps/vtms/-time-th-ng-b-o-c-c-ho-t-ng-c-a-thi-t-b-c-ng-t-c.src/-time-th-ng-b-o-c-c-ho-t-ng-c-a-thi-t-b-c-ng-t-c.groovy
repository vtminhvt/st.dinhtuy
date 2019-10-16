definition(
    name: "[Time]Thông báo các hoạt động của thiết bị, công tắc",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Chỉ nhận thông báo trong khoảng thời gian nhất định",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")


preferences {
    section("Chọn thông số cho kịch bản")
    {
     input name:"sel",type:"enum", title:"Chọn ON để kích hoạt kịch bản", options: ["on","off"], defaultValue:"off"
     input name: "timeB", type: "time", title: "Chọn giờ nhận thông báo"
     input name: "timeE", type: "time", title: "và giờ kết thúc"
     input("sw1","capability.switch",title:"Chọn danh sách thiết bị, công tắc cần nhận thông báo", multiple:true, required:true)     
     input name:"txt1",type:"text", title:"Với thông báo khi Mở ",defaultValue:"Mở"
     input name:"txt2",type:"text", title:"Với thông báo khi Tắt ",defaultValue:"Tắt"
     }
    
}
def installed() 
{
	init()
}
def updated() 
{
	init()	
}
def init()
{
    subscribe(sw1,"switch",sw_1)
}

def sw_1(evt)
{
def timeofB = timeToday(timeB)
def timeofE= timeToday(timeE)
def timeC=	now()

	def dk1= (timeofB<timeofE) && (timeC >= timeofB.time && timeC<=timeofE.time)
    def dk2= (timeofB>timeofE) && (timeC >= timeofB.time || timeC<=timeofE.time)
    def dk= (sel == "on")&& (dk1 || dk2)
    if (dk)
    {
        if(evt.value=="on") 
  			{sendPush( "${evt.displayName}: ${txt1}")}
 		 else 
  		if(evt.value=="off") 	
  			{sendPush("${evt.displayName}: ${txt2}")}
  		 }
}

def tb(msg)
{
	sendPush(msg)
}