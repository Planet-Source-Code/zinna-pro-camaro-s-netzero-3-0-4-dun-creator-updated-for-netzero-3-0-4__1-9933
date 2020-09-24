VERSION 5.00
Begin VB.Form AndyNZ 
   BackColor       =   &H80000007&
   BorderStyle     =   3  'Fixed Dialog
   Caption         =   "Andy's NetZero Password Maker"
   ClientHeight    =   1980
   ClientLeft      =   2100
   ClientTop       =   4860
   ClientWidth     =   9585
   BeginProperty Font 
      Name            =   "Tahoma"
      Size            =   8.25
      Charset         =   0
      Weight          =   400
      Underline       =   0   'False
      Italic          =   0   'False
      Strikethrough   =   0   'False
   EndProperty
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   132
   ScaleMode       =   3  'Pixel
   ScaleWidth      =   639
   Begin VB.CommandButton cmdClose 
      Caption         =   "Close"
      Default         =   -1  'True
      Height          =   315
      Left            =   3090
      TabIndex        =   10
      Top             =   1485
      Width           =   3750
   End
   Begin VB.TextBox NZversion 
      Height          =   285
      Left            =   5040
      TabIndex        =   8
      Text            =   "2.2.2"
      Top             =   990
      Width           =   1110
   End
   Begin VB.TextBox PassReal 
      Height          =   285
      Left            =   6570
      Locked          =   -1  'True
      TabIndex        =   4
      Top             =   585
      Width           =   2760
   End
   Begin VB.TextBox UserReal 
      Height          =   285
      Left            =   6570
      Locked          =   -1  'True
      TabIndex        =   3
      Top             =   225
      Width           =   2760
   End
   Begin VB.TextBox UserFake 
      Height          =   285
      Left            =   1680
      TabIndex        =   0
      Top             =   180
      Width           =   2760
   End
   Begin VB.TextBox PassFake 
      Height          =   285
      Left            =   1680
      TabIndex        =   1
      Top             =   540
      Width           =   2760
   End
   Begin VB.Line Line2 
      BorderColor     =   &H80000014&
      X1              =   320
      X2              =   320
      Y1              =   61
      Y2              =   1
   End
   Begin VB.Line Line1 
      BorderColor     =   &H80000010&
      X1              =   321
      X2              =   321
      Y1              =   62
      Y2              =   2
   End
   Begin VB.Label Label5 
      Alignment       =   2  'Center
      AutoSize        =   -1  'True
      BackStyle       =   0  'Transparent
      Caption         =   "NetZero Version"
      ForeColor       =   &H80000009&
      Height          =   195
      Left            =   3480
      TabIndex        =   9
      Top             =   1065
      Width           =   1170
   End
   Begin VB.Label Label1 
      Alignment       =   2  'Center
      AutoSize        =   -1  'True
      BackStyle       =   0  'Transparent
      Caption         =   "NetZero Username :"
      ForeColor       =   &H80000009&
      Height          =   195
      Left            =   120
      TabIndex        =   7
      Top             =   240
      Width           =   1485
   End
   Begin VB.Label Label4 
      Alignment       =   2  'Center
      AutoSize        =   -1  'True
      BackStyle       =   0  'Transparent
      Caption         =   "Dial-up Username :"
      ForeColor       =   &H80000009&
      Height          =   195
      Left            =   5010
      TabIndex        =   6
      Top             =   210
      Width           =   1395
   End
   Begin VB.Label Label3 
      Alignment       =   2  'Center
      AutoSize        =   -1  'True
      BackStyle       =   0  'Transparent
      Caption         =   "Dial-up Password :"
      ForeColor       =   &H80000009&
      Height          =   195
      Left            =   5010
      TabIndex        =   5
      Top             =   645
      Width           =   1350
   End
   Begin VB.Label Label2 
      Alignment       =   2  'Center
      AutoSize        =   -1  'True
      BackStyle       =   0  'Transparent
      Caption         =   "NetZero Password :"
      ForeColor       =   &H80000009&
      Height          =   195
      Left            =   120
      TabIndex        =   2
      Top             =   600
      Width           =   1440
   End
End
Attribute VB_Name = "AndyNZ"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
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
Option Explicit
Private Sub cmdClose_Click()
    Unload Me
End Sub

Private Sub NZversion_Change()
 UserReal = User(UserFake, NZversion)
End Sub

Private Sub PassFake_Change()
    PassReal = Pass(PassFake) 'call the decription function
End Sub
Private Sub UserFake_Change()
    UserReal = User(UserFake, NZversion)
End Sub
