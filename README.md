<div align="center">

## Camaro's NetZero 3\.0\.4  DUN Creator \(Updated for Netzero 3\.0\.4\)

<img src="PIC20007290546849.gif">
</div>

### Description

The main purpose is to access Netzero through my Windows CE Device.

But can also be used to Access NetZero Faster, and without the banners.

This program allows you to convert your current netzero account so you don't have to use the netzero software.

You can surf at faster speeds because there are no banners loading,

just enter your username and password and it will tell you the codes to set in a Dial Up account.

To use on 3.0.4 change the version to 0:3.0.4

Check out http://pass.ezcpufix.com for an online version. Use an Active X enabled browser!
 
### More Info
 
To use on 3.0.4 change the version to 0:3.0.4

Very Detailed Code Source.

Easy For Everyone.

Web Based version at http://pass.ezcpufix.com

The Valid Dial-Up Username and Password for netzero.

To use on 3.0.4 change the version to 0:3.0.4


<span>             |<span>
---                |---
**Submitted On**   |2000-12-11 14:36:22
**By**             |[Zinna Pro](https://github.com/Planet-Source-Code/PSCIndex/blob/master/ByAuthor/zinna-pro.md)
**Level**          |Advanced
**User Rating**    |3.6 (18 globes from 5 users)
**Compatibility**  |VB 6\.0
**Category**       |[Internet/ HTML](https://github.com/Planet-Source-Code/PSCIndex/blob/master/ByCategory/internet-html__1-34.md)
**World**          |[Visual Basic](https://github.com/Planet-Source-Code/PSCIndex/blob/master/ByWorld/visual-basic.md)
**Archive File**   |[CODE\_UPLOAD1258912112000\.zip](https://github.com/Planet-Source-Code/zinna-pro-camaro-s-netzero-3-0-4-dun-creator-updated-for-netzero-3-0-4__1-9933/archive/master.zip)

### API Declarations

```
Function Pass(password)
Key1 = "`-=~!@#$%^&*()_+[]\{}|;':" _
& """" & ",./<>?abcdefghijklmnopqrstuvwxyzABCDEFG" _
& "HIJKLMNOPQRSTUVWXYZ0123456789"
Key2 = "GFEDCBAzyxwvutsrqponmlkjihgfed" _
& "cba?></.," & """" & ":';|}{\][+_)(*&^%$#@" _
& "!~=-`9876543210ZYXWVUTSRQPONMLKJIH"
 For i = 1 To Len(password)
 A = Mid(password, i, 1)
 B = InStr(1, Key1, A)
 C = B - (i - 1)
 D = Mid(Key2, C, 1)
 E = E + D
 Next i
 If E = "" Then
 Else
 Pass = "0" & E & "1"
 End If
End Function
Function User(Username, Version)
If Username = "" Then
User = ""
Else
 If Version = "" Then
 User = "2.2.2:" & Username & "@netzero.net"
 Else
 User = Version & ":" & Username & "@netzero.net"
 End If
 End If
End Function
To Use on Netzero Zeroport 3.0.4 change the version to 0:3.0.4
This would cause netzero to connect.
```





