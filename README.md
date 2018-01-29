### Donny's ToolBox

## little practice of kotlin and flask [server](https://github.com/donnydonny123/ToolBoxServer)

# Day3
- add download file from server //TODO choose file
- in order to avoid put filename in url, use `/files/<int:index>` in query (`/files`)
***
>手機上會有特殊字元檔案名稱無法顯示的問題 可是不會影響傳送
>
>介面上把textview移到下方 之後再解決鍵盤按出來之後會遮住textview的問題
>
>不要甚麼都印log阿 會炸記憶體的

# Day1 & 2
- Kotlin and Okhttp/fuel to connect to [Server](https://github.com/donnydonny123/ToolBoxServer) 
- Github stuff (ahhh) 
***
>使用Okhttp時不想要用handler來更新UI Thread, 後來找到[一篇文](https://medium.com/@macastiblancot/android-coroutines-getting-rid-of-runonuithread-and-callbacks-cleaner-thread-handling-and-more-234c0a9bd8eb)
用coroutine來實作網路和response update in UI (豪猛) 
>
>code很乾淨 但是後來發現kotlin寫handler也不會很長 `runOnUIThread{//UI code}` 
>
>而且另一個library -- [fuel](https://github.com/kittinunf/Fuel) 有實作file download, 就改成fuel來做internet connection了
