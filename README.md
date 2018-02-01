### Donny's ToolBox

## little practice of kotlin and flask [server](https://github.com/donnydonny123/ToolBoxServer)

# Day 4
- new activity when user click download -> select which file to download
***
>使用了[gson來parse server的檔案資料](https://stackoverflow.com/questions/44117970/kotlin-data-class-from-json-using-gson), 目前決定先傳檔案名稱和最後修改時間
>
>複習怎麼切換activity和listView的用法, listView用之前java的寫法, 可能有更簡潔的寫法
>
>之後要加欄位應該也不會太複雜

# Day 3
- add download file from server //TODO choose file
- in order to avoid put filename in url, use `/files/<int:index>` in query (`/files`)
***
>手機上會有特殊字元檔案名稱無法顯示的問題 可是不會影響傳送
>
>介面上把textview移到下方 之後再解決鍵盤按出來之後會遮住textview的問題
>
>不要甚麼都印log阿 會炸記憶體的

# Day 1 & 2
- Kotlin and Okhttp/fuel to connect to [Server](https://github.com/donnydonny123/ToolBoxServer) 
- Github stuff (ahhh) 
***
>使用Okhttp時不想要用handler來更新UI Thread, 後來找到[一篇文](https://medium.com/@macastiblancot/android-coroutines-getting-rid-of-runonuithread-and-callbacks-cleaner-thread-handling-and-more-234c0a9bd8eb)
用coroutine來實作網路和response update in UI (豪猛) 
>
>code很乾淨 但是後來發現kotlin寫handler也不會很長 `runOnUIThread{//UI code}` 
>
>而且另一個library -- [fuel](https://github.com/kittinunf/Fuel) 有實作file download, 就改成fuel來做internet connection了
