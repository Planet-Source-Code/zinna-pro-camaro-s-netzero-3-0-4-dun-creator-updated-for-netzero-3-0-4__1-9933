Attribute VB_Name = "NZPass"
'Andy Netzero Password Decoder
'------------------------------
'Andy NetZero Password Decoder
'------------------------------
'This program allows you to
'convert your current netzero
'account so you don't have to use
'the netzero software. You can
'surf at faster speeds because
'there are no banners loading
'just enter your username and
'password and it will tell you
'the codes to set in a Dial Up
'account. In future versions
'i am wanting to have this
'program create a Dial Up
'Networking account and set the
'username and password so the user
'can have a easly connection.
'Also in the future i wish to
'Set the Auto Connect Feature
'Enabler into this program.
'
' If you have any questions or comments
' please send them to camaroman@bigfoot.com

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










'Down here is the (Close some changes) same as above just with comments
'Take this out this is just for explination



Function Pass2(password) 'The Same Exact Function as Pass but With Captions Converts to Dialup

'Define the First Key
Key1 = "`-=~!@#$%^&*()_+[]\{}|;':" _
& """" & ",./<>?abcdefghijklmnopqrstuvwxyzABCDEFG" _
& "HIJKLMNOPQRSTUVWXYZ0123456789"

'Define the Second Key
Key2 = "GFEDCBAzyxwvutsrqponmlkjihgfed" _
& "cba?></.," & """" & ":';|}{\][+_)(*&^%$#@" _
& "!~=-`9876543210ZYXWVUTSRQPONMLKJIH"

   'Loop through the password
   For i = 1 To Len(password)
     
        'Set A as the Current Char in the loop
        A = Mid(password, i, 1)
        
        'Compare the Current Char with Key & give its Pos on Key1
        B = InStr(1, Key1, A)
        
        'Take the Current Char Pos at Key1 & - the # of Everything up to it in the Pass
        C = B - (i - 1)
        
        'Use the # of Everything up to the Char find out where it is located on Key2
        D = Mid(Key2, C, 1)
        
        'Combine all of the Converted Char together
        E = E + D
    
   'Go Back and Repeat the Process until every Char has been Converted
   Next i
    
   'Take the Full Converted Password and Put a 0 at the front and a 1 at the end
   Pass2 = "0" & E & "1"

End Function
Function User2(Username, Version) 'The Same Exact Function as User but With Captions Converts to Dialup
    If Version = "" Then
       'If there is no version then give 2.2.2
       User2 = "2.2.2:" & Username & "@netzero.net"
    Else
       'If there is a version then give the version given
       User2 = Version & ":" & Username & "@netzero.net"
    End If
End Function

