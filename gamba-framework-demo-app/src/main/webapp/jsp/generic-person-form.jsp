
		<input type="hidden" name="id" value="${person.id != null ? person.id : 0}" />
		name: <input type="text" name="name" size="30" value="${person.name}" />${validationErrorMap['name']}<br />
		age:  <input type="text" name="age"  size="5"  value="${person.age}" /> ${validationErrorMap['age']}<br />
		<br />
		<br />
