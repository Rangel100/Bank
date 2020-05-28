package co.edu.usbcali.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operacion/")
public class OperacionesMatematicas {

	@GetMapping("sumar/{numero1}/{numero2}")
	public Respuesta sumar(@PathVariable("numero1") Integer n1, @PathVariable("numero2") Integer n2) {
		return new Respuesta(n1 + n2);
	}

	@GetMapping("restar/{numero1}/{numero2}")
	public Respuesta restar(@PathVariable("numero1") Integer n1, @PathVariable("numero2") Integer n2) {
		return new Respuesta(n1 - n2);
	}
}

class Respuesta {

	private Integer valor;

	public Respuesta(Integer valor) {
		super();
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

}