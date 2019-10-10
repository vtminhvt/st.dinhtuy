definition(
    name: "[Tiện ích]Đặt lời chào lúc bình minh và hoàng hôn",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Tiện ích lời chào khi trời sáng",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")

preferences 
{
 section("Chọn thông số cho kịch bản")
    {
        input name:"sel",type:"enum", title:"Chọn ON để kích hoạt kịch bản", options: ["on","off"], defaultValue:"off"
        input name:"txt1",type:"text", title:"Với thông báo khi bắt đầu buổi sáng",defaultValue:"LMặt trời đã mọc, chúc một ngày tốt lành"
       	input name:"txt2",type:"text", title:"và thông báo khi mặt trời lặn",defaultValue:"Đã đến lúc nghỉ ngơi, cần nạp lại năng lượng cho ngày tiếp theo!"
    } 
}

def installed() 
{
    //schedule(timeCB, cb)
    subscribe(location, "sunset", sunsetHandler)
    subscribe(location, "sunrise", sunriseHandler)
}

def updated() 
{
	unschedule()
	//schedule(timeCB, cb)
}



def sunriseHandler(evt)
{
if (sel=="on")
	{
		sendPush("${txt1}: ${evt.displayName} ")
	}

}

def sunsetHandler(evt)
{
if (sel=="on")
	{
		sendPush("${txt2}: ${evt.displayName} ")
	}	
}