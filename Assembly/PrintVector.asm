.data
	vector: .byte 7,3,5,10,12
.text
	la $t0, vector
	# Pegando o final do vetor
	addi $t1, $t0, 5
	li $t2, 0
	
runVector:
	li $v0, 1
	# Com o () retorna-se o valor do endereço de memória
	add $t2, $a0, $t2
	syscall
	
	# Indo para o próximo indice do vetor
	addi $t0, $t0, 1
	
	blt $t0, $t1, runVector
	
	li $v0, 1
	move $a0, $t2
	syscall
	
	
	
	
	  

