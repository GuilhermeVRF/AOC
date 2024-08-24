.data
	message_man: .asciiz "Informe a idade de um homem: "
	message_woman: .asciiz "Informe a idade de uma mulher: "
	message_sum: .asciiz "Homem mais velho + Mulher mais nova: "
	message_mult: .asciiz "Homem mais novo * Mulher mais velha: "
.text
	li $v0, 4
	la $a0, message_man
	syscall
	
	li $v0, 5
	syscall
	
	# t0 -> Idade do 1° homem
	move $t0, $v0
	
	li $v0, 4
	la $a0, message_man
	syscall
	
	li $v0, 5
	syscall
	
	# t1 -> Idade do 2° homem
	move $t1 $v0
	
	li $v0, 4
	la $a0, message_woman
	syscall
	
	li $v0, 5
	syscall
	
	# t2 -> Idade da 1° mulher
	move $t2, $v0
	
	li $v0, 4
	la $a0, message_woman
	syscall
	
	li $v0, 5
	syscall
	
	# t3 -> Idade da 2° mulher
	move $t3, $v0
	
	# Checando se idade do 1° homem é menor que o do 2°
	bgt  $t0, $t1, firstManGreater
	bge $t1, $t0, secondManGreater
	man_returnPoint:
	
	# Checando se idade do 1° homem é menor que o do 2°
	bgt  $t2, $t3, firstWomanGreater
	bge $t3, $t2, secondWomanGreater
	woman_returnPoint:
	
	# Somando homem mais velho com mulher mais nova
	li $v0, 4
	la $a0, message_sum
	syscall
	
	add $t8, $t4, $t7 
	li $v0, 1
	move $a0, $t8
	syscall

	# Multiplicando homem mais novo com mulher mais velha
	li $v0, 4
	la $a0, message_mult
	syscall
	
	mulo $t9, $t5, $t6 
	li $v0, 1
	move $a0, $t9
	syscall
			
	j end
	
firstManGreater:
	# t4 armazenará a idade do homem mais velho - Nesse caso o 0
	move $t4, $t0
	# t5 armazenará a idade do homem mais novo - Nesse caso o 1
	move $t5, $t1
	
	j man_returnPoint
	
secondManGreater:
	# t4 armazenará a idade do homem mais velho - Nesse caso o 0
	move $t4, $t1
	# t5 armazenará a idade do homem mais novo - Nesse caso o 1
	move $t5, $t0
	
	j man_returnPoint
	
firstWomanGreater:
	# t4 armazenará a idade da mulher mais velha - Nesse caso o 2
	move $t6, $t2
	# t5 armazenará a idade do mulher mais nova - Nesse caso o 3
	move $t7, $t3
	
	j woman_returnPoint
secondWomanGreater:
	# t4 armazenará a idade da mulher mais velha - Nesse caso o 2
	move $t6, $t3
	# t5 armazenará a idade da mulher mais nova - Nesse caso o 3
	move $t7, $t2
	
	j woman_returnPoint
	
end:
