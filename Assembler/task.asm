; Opredelit dlinu stroki. Esli chetnaya - zamenit vse probeli na *
.386
.model flat, stdcall
option casemap : none

includelib  c:\masm32\lib\kernel32.lib
include     c:\masm32\include\kernel32.inc

.data
string db ' SOME STRING WITH SPACES    ',0d
ptrBuffer dd 256 dup(0)
_out dd 0
message1 db 'Source string:',0d
message2 db 'String length:',0d
message3 db 'Result string:',0d
messageRN db 13,10
number db 0d,0d
.code
start:
    invoke GetStdHandle, -11d
    mov _out, eax
    invoke WriteConsole, _out, offset message1, sizeof message1, 0d, 0d
    invoke WriteConsole, _out, offset string, sizeof string, 0d, 0d
    invoke WriteConsole, _out, offset messageRN, 2d, 0d, 0d
    
        mov eax, offset string
        mov ecx, 0d
        mov ebx, offset ptrBuffer
    process:
        cmp byte ptr [eax], 0d
        je lastStep
        cmp byte ptr [eax], ' '
        je spaceFound
    stepAfterSpaceFound:
        inc ecx
        inc eax
        jmp process
    spaceFound:
        mov dword ptr [ebx], eax
        add ebx, 4d
        jmp stepAfterSpaceFound
    lastStep:
        test ecx, 1d
        jnz endProcess
        mov ebx, offset ptrBuffer
        push ecx
    replaceProcess:
        cmp dword ptr [ebx], 0d
        je endProcess
        mov ecx, dword ptr [ebx]
        mov byte ptr [ecx], '*'
        add ebx, 4d
        jmp replaceProcess
    endProcess:
        pop ecx
        mov eax, ecx
        aam
        add ax, 3030h
        mov number, ah
        mov number+1, al
        invoke WriteConsole, _out, offset message2, sizeof message2, 0d, 0d
        invoke WriteConsole, _out, offset number, 2d, 0d, 0d
        invoke WriteConsole, _out, offset messageRN, 2d, 0d, 0d
        invoke WriteConsole, _out, offset message3, sizeof message3, 0d, 0d
        invoke WriteConsole, _out, offset string, sizeof string, 0d, 0d
        
    _exit:
    invoke Sleep, 15000d
    invoke ExitProcess, 0d
end start