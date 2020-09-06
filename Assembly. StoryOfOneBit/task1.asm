.386
.model flat, stdcall
option casemap : none

includelib  c:\masm32\lib\kernel32.lib
include     c:\masm32\include\kernel32.inc
.data
string_source db '_____ HELLLLLO STRINGGGGGG III ASDASDASD)!@#)!@#()!@#!@#_asdasdfg',0
string_result db 256 dup(0)
bitmask db 32 dup(0)
; bitmask have 256 bits [0..255] so every bit shows is current
; character(to what symbol EAX points) of source string with code=pos of bit
; is already included in result string
; 0 - not included
; 1 - included
;
; D7660A91093F66089C2E7A4C8D248670AF4E40C1E5EF8FB65599C5C589E44A09

msgSrcStr db 'Source string:',0
msgResStr db 'Result string:',0
msgResult db '---Result---',13,10
msgSrcLen db 'Source string length =',0
msgResLen db 'Result string length =',0
msgRN     db 13,10
out1      dd 0
srclen    dd 0
reslen    dd 0
number    db 0,0
bit       dd 0
.code
start:
    invoke GetStdHandle, -11d
    mov out1, eax
    
    invoke WriteConsole, out1, offset msgSrcStr, sizeof msgSrcStr, 0d, 0d
    invoke WriteConsole, out1, offset string_source, sizeof string_source, 0d, 0d
    
    mov eax, offset string_source
    mov ecx, 0
    mov edx, offset string_result
    procStart:
        mov ebx, offset bitmask
        cmp byte ptr [eax], 0d
        je procEnd
        ;
        ;---- saving eax, ecx, ebx, edx to stack
        push edx
        push eax
        inc ecx
        push ecx
        push ebx
        ;
        ;---- put curr symbol code to eax
        xor ebx, ebx
        mov bl, byte ptr [eax]
        xor eax, eax
        mov al, bl 
        ;al contains code of curr symbol
        ;
        ;---- divide code by 8
        mov bl, 8d
        div bl
        ; al - WHOLE part of division;    ah - REMAINDER
        ;
        ;now choosing right byte from bitmask.
        ;moves pointer to bitmask for _WHOLE PART_ of division
        pop ebx             ; ebx - pointer to bitmask array
        push eax            ; save eax to stack
        xor ah, ah 
        add ebx, eax        ; moves ebx to right byte
        ;
        ;now choosing right bit from current bitmask for current symbol
        ;ebx points to right byte from bitmask
        pop eax             ; restore eax
        mov al, ah          ; moves REMAINDER of division to al
        xor ah, ah          ; REMAINDER shows us offset of byte start (00000001) to choose right bit
        mov ecx, 1          ; moves 1 to ecx - choosing right bit
        ;
        ; while ah != 0 moves ecx bit to left (00000001 -> 00000010)...
        cycle:
            cmp al, 0d
            je cycleEnd
            shl ecx, 1d
            sub al, 1
            jmp cycle
        cycleEnd:
        ;
        ; ebx points to right byte from bitmask
        ; ecx is right bit
        ; make AND ecx, ebx shows us if bit from byte that ebx points equals 0?
        ; if not 0 current symbol of string exists in result string so jump to next symbol
        ; but before resotre ecx and eax from stack
        ;
        mov bit, ecx ; store right bit. we need it later
        push ebx ; store point to right byte
        ;
        mov bl, byte ptr [ebx] ; mov to ebx current byte
        and cl, bl            ;make AND for our bit
        cmp cl, 0d
        ; restore registers from stack
        pop ebx ; restore pointer to right byte
        pop ecx ; count of symbols
        pop eax ; pointer to source string
        pop edx ; pop pointer to result string byte
        ; jmp next symbol if current exists in result str
        jne nextChar
        ;
        ;if AND ECX, EBX equals 0 that means current symbol not in result string
        ;so add it to result string and check next symbol
        push edx
        mov edx, bit
        add byte ptr [ebx], dl  ;set current bit to 1
        ;
        ;now needs to save current symbol to result str
        pop edx ; restore pointer to byte of result string
        push ecx ; save current symbols count to stack
        mov cl, byte ptr [eax]
        mov byte ptr [edx], cl
        inc edx
        pop ecx
        nextChar:
        inc eax
        jmp procStart
    procEnd:
    ; ecx - count of symbols in source string
    ; edx - points to zero in result string
    ;
    ; now set edx to count of symbols in result string
    ; just sub [offset result_string] from edx
    sub edx, offset string_result
    ; ok now we needs just to print our result
    ;
    mov srclen, ecx
    mov reslen, edx
    
    invoke WriteConsole, out1, offset msgRN, 2d, 0d, 0d
    invoke WriteConsole, out1, offset msgResult, sizeof msgResult, 0d, 0d
    invoke WriteConsole, out1, offset msgSrcLen, sizeof msgSrcLen, 0d, 0d
    mov eax, srclen
    aam
    add eax, 3030h
    mov number, ah
    mov number+1, al
    invoke WriteConsole, out1, offset number, 2d, 0d, 0d
    invoke WriteConsole, out1, offset msgRN, 2d, 0d, 0d
    invoke WriteConsole, out1, offset msgResLen, sizeof msgResLen, 0d, 0d
    mov eax, reslen
    aam
    add eax, 3030h
    mov number, ah
    mov number+1, al
    invoke WriteConsole, out1, offset number, 2d, 0d, 0d
    invoke WriteConsole, out1, offset msgRN, 2d, 0d, 0d
    invoke WriteConsole, out1, offset msgResStr, sizeof msgResStr, 0d, 0d
    invoke WriteConsole, out1, offset string_result, reslen, 0d, 0d
    
    invoke Sleep, 15000d
    invoke ExitProcess, 0d
end start