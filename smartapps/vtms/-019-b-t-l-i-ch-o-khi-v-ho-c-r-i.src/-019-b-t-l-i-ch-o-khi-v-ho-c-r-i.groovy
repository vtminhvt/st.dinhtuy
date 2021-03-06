definition(
    name: "[019]Bật lời chào khi về hoặc rời",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Bạn muốn hệ thống thực hiện lời chào",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")


preferences 
{
    section("Chọn thông số kịch bản")
    {
        input name:"sel",type:"enum", title:"Chọn ON để kích hoạt kịch bản", options: ["on","off"], defaultValue:"off"
        input("presence","capability.presenceSensor",title:"Cảm biến hiện diện tương ứng với thành viên", multiple:true, required:true)
      	input name:"txt1",type:"text", title:"với nội dung khi tôi về nhà",defaultValue:"Đã về nhà"
       	input name:"txt2",type:"text", title:"và nội dung khi tôi rời khỏi nhà",defaultValue:"Đã rời khỏi nhà, chúc an toàn!"
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
    subscribe(presence,"presence",presence_1)
}

def presence_1(evt)
{
	if (sel=="on")
	{
        if(evt.value=="present")
        {
            sendPush("${txt1}: ${evt.displayName} ")
        }

        if(evt.value=="not present")
        {
            sendPush("${txt2}: ${evt.displayName}")
        }
   } 
}