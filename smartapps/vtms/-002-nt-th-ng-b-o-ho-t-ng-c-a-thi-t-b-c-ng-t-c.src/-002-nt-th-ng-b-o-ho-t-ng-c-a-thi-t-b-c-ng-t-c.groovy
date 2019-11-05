definition(
    name: "[002-nT]Thông báo hoạt động của thiết bị, công tắc",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Giao tiếp",
    category: "Safety & Security",
   iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")


preferences {
     
    section("Chọn thông số cho kịch bản")
    {
    	input name:"sel",type:"enum", title:"Chọn ON để kích hoạt kịch bản", options: ["on","off"], defaultValue:"off"
    	input("swCC","capability.switch",title:"Chọn danh sách thiết bị, công tắc bạn muốn nhận thông báo trạng thái", multiple:true, required:true)
        input name:"txt1",type:"text", title:"Với thông báo khi Mở ",defaultValue:"Bật"
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
    subscribe(swCC,"switch",sw_CC)
   
}


def sw_CC(evt)

{
if (sel=="on")
{
  if(evt.value=="on") 
  	{sendPush( "${evt.displayName}: ${txt1}")}
  else 
  if(evt.value=="off") 	
  	{sendPush("${evt.displayName}: ${txt2}")}
 
}
}