.data
	number_message: .asciiz "Informe um número: "
	true: .asciiz "TRUE"
	false: .asciiz "FALSE"
.text
	li $v0, 4
	la $a0, number_message
	syscall
	
	li $v0, 5
	syscall
	
	# Movendo o número informado para um registrador
	move $t0, $v0
	# Inicializando contador de números
	move $t1, $zero
	# Inicialziando a variável de some
	move $t2,$zero
	
	j percorrerNumeros
	
percorrerNumeros:

	div $t0, $t1
	mfhi $t4
	beqz $t4, soma 

continuarLoop:
	addi $t1, $t1, 1
	blt $t1, $t0, percorrerNumeros
	
	sub $t3, $t0, $t2
	beqz $t3, perfectNumber
	j notPerfectNumber

soma:

	add $t2, $t2, $t1
	j continuarLoop

perfectNumber:

	li $v0, 4
	la $a0, true
	syscall
	j fim

notPerfectNumber:

	li $v0, 4
	la $a0, false
	syscall
	j fim

fim: