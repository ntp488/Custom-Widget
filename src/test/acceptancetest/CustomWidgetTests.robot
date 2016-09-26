*** Settings ***
Library         SwingLibrary
Library         keywords.CustomWidgetKeywords


*** Keywords ***

    
    
*** Test Cases ***
Hexagon Is Selected Initially
    Start Application       edu.jsu.mcis.Main
    Select Window           Main
    Label Text Should Be    label   Hexagon
    Close Window            Main

Widget Is Selected After Center Click
    Start Application       edu.jsu.mcis.Main
    Select Window           Main
    Click Custom Widget Inside
    Label Text Should Be    label   Hexagon
    Close Window            Main
    
Widget Is Unchanged After Edge Click
    Start Application       edu.jsu.mcis.Main
    Select Window           Main
    Click Custom Widget Outside
    Label Text Should Be    label   Hexagon
    Close Window            Main

Widget Toggles With Successive Center Clicks
    Start Application       edu.jsu.mcis.Main
    Select Window           Main
    Click Custom Widget Inside
    Label Text Should Be    label   Hexagon
    Click Custom Widget Inside
    Label Text Should Be    label   Hexagon
    Close Window            Main
