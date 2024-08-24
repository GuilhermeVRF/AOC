.data
	textOutput: .asciiz "Informe um número:"
	storagedNumbers: .word 10
.text
	la $t0, storagedNumbers
	add $t1, $t0, 40
entradaDados:
	li $v0, 4
	la $a0, textOutput
	syscall
	
	li $v0, 5
	syscall
	
	sb $v0, ($t0)
	
	add $t0, $t0, 4
	blt $t0, $t1, entradaDados
imprimirVetor:
	li $v0, 4
	lw $a0, ($t1)
	syscall
	
	sub $t0, $t0, 4
	bge $t0, 0, imprimirVetor	
	
		
	