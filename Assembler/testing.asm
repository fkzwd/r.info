.386
.model flat, stdcall
option casemap : none

includelib  c:\masm32\lib\kernel32.lib
include     c:\masm32\include\kernel32.inc

.data
msg1 db 'abcd',0
ptrBuf dd 4 dup (0)
.code
start:
    mov eax, offset msg1
    mov ebx, offset ptrBuf
    mov dword ptr [ebx], eax
    mov ecx, dword ptr [ebx]
    mov byte ptr [ecx], '1'
    xor edx, edx
    mov dl, msg1
    
    invoke Sleep, 15000d
    invoke ExitProcess, 0d
end start