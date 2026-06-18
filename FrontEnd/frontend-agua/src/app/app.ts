import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { UsuarioService } from './services/usuario.service';
import { ViviendaService } from './services/vivienda.service';
import { CuotaService } from './services/cuota.service';
import { PagoService } from './services/pago.service';

import { Usuario } from './models/usuario.model';
import { UsuarioRequest } from './models/usuario-request.model';

import { Vivienda } from './models/vivienda.model';
import { ViviendaRequest } from './models/vivienda-request.model';

import { Cuota } from './models/cuota.model';
import { CuotaRequest } from './models/cuota-request.model';
import { GenerarCuotasRequest } from './models/generar-cuotas-request.model';

import { Pago } from './models/pago.model';
import { PagoRequest } from './models/pago-request.model';

import { EstadoCuentaViviendaResponse } from './models/cuenta-vivienda-response.model';

@Component({
  selector: 'app-root',
  imports: [CommonModule, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  seccionActiva = signal<'usuarios' | 'viviendas' | 'cuotas' | 'pagos' | 'estado-cuenta'>('usuarios');

  usuarios = signal<Usuario[]>([]);
  viviendas = signal<Vivienda[]>([]);
  cuotas = signal<Cuota[]>([]);
  pagos = signal<Pago[]>([]);

  usuarioEditandoId = signal<number | null>(null);
  viviendaEditandoId = signal<number | null>(null);
  cuotaEditandoId = signal<number | null>(null);
  pagoEditandoId = signal<number | null>(null);

  estadoCuenta = signal<EstadoCuentaViviendaResponse | null>(null);
  viviendaSeleccionadaEstadoCuenta = 0;

  pagoSeleccionadoRecibo = signal<Pago | null>(null);

  mensajeExito = signal('');
  mensajeError = signal('');

  nuevoUsuario: UsuarioRequest = {
    nombre: '',
    apellido: '',
    dpi: '',
    telefono: ''
  };

  nuevaVivienda: ViviendaRequest = {
    barrio: '',
    referencia: '',
    usuarioId: 0
  };

  nuevaCuota: CuotaRequest = {
    mes: 1,
    anio: new Date().getFullYear(),
    monto: 30,
    fechaVencimiento: '',
    estadoCuota: 'PENDIENTE',
    viviendaId: 0
  };

  generarCuotasRequest: GenerarCuotasRequest = {
    mes: new Date().getMonth() + 1,
    anio: new Date().getFullYear(),
    monto: 30
  };

  nuevoPago: PagoRequest = {
    fechaPago: new Date().toISOString().split('T')[0],
    montoPagado: 0,
    metodoPago: 'EFECTIVO',
    observacion: '',
    cuotaId: 0
  };

  constructor(
    private usuarioService: UsuarioService,
    private viviendaService: ViviendaService,
    private cuotaService: CuotaService,
    private pagoService: PagoService
  ) {}

  ngOnInit(): void {
    this.cargarUsuarios();
    this.cargarViviendas();
    this.cargarCuotas();
    this.cargarPagos();
  }

  cambiarSeccion(seccion: 'usuarios' | 'viviendas' | 'cuotas' | 'pagos' | 'estado-cuenta'): void {
    this.seccionActiva.set(seccion);
    this.limpiarMensajes();
  }

  limpiarMensajes(): void {
    this.mensajeExito.set('');
    this.mensajeError.set('');
  }

  mostrarExito(mensaje: string): void {
    this.mensajeExito.set(mensaje);
    this.mensajeError.set('');
  }

  mostrarError(mensaje: string): void {
    this.mensajeError.set(mensaje);
    this.mensajeExito.set('');
  }

  cargarUsuarios(): void {
    this.usuarioService.listarUsuarios().subscribe({
      next: (data) => {
        this.usuarios.set(data);
      },
      error: (error) => {
        console.error('Error al cargar usuarios', error);
        this.mostrarError('No se pudieron cargar los usuarios');
      }
    });
  }

  guardarOActualizarUsuario(): void {
    const id = this.usuarioEditandoId();

    if (id !== null) {
      this.usuarioService.actualizarUsuario(id, this.nuevoUsuario).subscribe({
        next: () => {
          this.mostrarExito('Usuario actualizado correctamente');
          this.cancelarEdicion();
          this.cargarUsuarios();
        },
        error: (error) => {
          console.error('Error al actualizar usuario', error);
          this.mostrarError('No se pudo actualizar el usuario');
        }
      });
    } else {
      this.usuarioService.guardarUsuario(this.nuevoUsuario).subscribe({
        next: () => {
          this.mostrarExito('Usuario guardado correctamente');
          this.cancelarEdicion();
          this.cargarUsuarios();
        },
        error: (error) => {
          console.error('Error al guardar usuario', error);
          this.mostrarError('No se pudo guardar el usuario');
        }
      });
    }
  }

  eliminarUsuario(id: number): void {
    const confirmado = confirm('¿Seguro que deseas eliminar este usuario?');

    if (!confirmado) {
      return;
    }

    this.usuarioService.eliminarUsuario(id).subscribe({
      next: () => {
        this.mostrarExito('Usuario eliminado correctamente');
        this.cargarUsuarios();
      },
      error: (error) => {
        console.error('Error al eliminar usuario', error);
        this.mostrarError('No se pudo eliminar el usuario');
      }
    });
  }

  seleccionarUsuarioParaEditar(usuario: Usuario): void {
    this.nuevoUsuario = {
      nombre: usuario.nombre,
      apellido: usuario.apellido,
      dpi: usuario.dpi,
      telefono: usuario.telefono
    };

    this.usuarioEditandoId.set(usuario.id);
    this.limpiarMensajes();
  }

  cancelarEdicion(): void {
    this.nuevoUsuario = {
      nombre: '',
      apellido: '',
      dpi: '',
      telefono: ''
    };

    this.usuarioEditandoId.set(null);
  }

  cargarViviendas(): void {
    this.viviendaService.listarViviendas().subscribe({
      next: (data) => {
        this.viviendas.set(data);
      },
      error: (error) => {
        console.error('Error al cargar viviendas', error);
        this.mostrarError('No se pudieron cargar las viviendas');
      }
    });
  }

  guardarOActualizarVivienda(): void {
    const id = this.viviendaEditandoId();

    if (id !== null) {
      this.viviendaService.actualizarVivienda(id, this.nuevaVivienda).subscribe({
        next: () => {
          this.mostrarExito('Vivienda actualizada correctamente');
          this.cancelarEdicionVivienda();
          this.cargarViviendas();
        },
        error: (error) => {
          console.error('Error al actualizar vivienda', error);
          this.mostrarError('No se pudo actualizar la vivienda');
        }
      });
    } else {
      this.viviendaService.guardarVivienda(this.nuevaVivienda).subscribe({
        next: () => {
          this.mostrarExito('Vivienda guardada correctamente');
          this.cancelarEdicionVivienda();
          this.cargarViviendas();
        },
        error: (error) => {
          console.error('Error al guardar vivienda', error);
          this.mostrarError('No se pudo guardar la vivienda');
        }
      });
    }
  }

  eliminarVivienda(id: number): void {
    const confirmado = confirm('¿Seguro que deseas eliminar esta vivienda?');

    if (!confirmado) {
      return;
    }

    this.viviendaService.eliminarVivienda(id).subscribe({
      next: () => {
        this.mostrarExito('Vivienda eliminada correctamente');

        if (this.viviendaEditandoId() === id) {
          this.cancelarEdicionVivienda();
        }

        this.cargarViviendas();
      },
      error: (error) => {
        console.error('Error al eliminar vivienda', error);
        this.mostrarError('No se pudo eliminar la vivienda');
      }
    });
  }

  seleccionarViviendaParaEditar(vivienda: Vivienda): void {
    this.nuevaVivienda = {
      barrio: vivienda.barrio,
      referencia: vivienda.referencia,
      usuarioId: vivienda.usuario.id
    };

    this.viviendaEditandoId.set(vivienda.id);
    this.limpiarMensajes();
  }

  cancelarEdicionVivienda(): void {
    this.nuevaVivienda = {
      barrio: '',
      referencia: '',
      usuarioId: 0
    };

    this.viviendaEditandoId.set(null);
  }

  obtenerUsuariosDisponibles(): Usuario[] {
    const viviendaEditandoId = this.viviendaEditandoId();

    return this.usuarios().filter(usuario => {
      const viviendaDelUsuario = this.viviendas().find(
        vivienda => vivienda.usuario.id === usuario.id
      );

      if (!viviendaDelUsuario) {
        return true;
      }

      return viviendaDelUsuario.id === viviendaEditandoId;
    });
  }

  cargarCuotas(): void {
    this.cuotaService.listarCuotas().subscribe({
      next: (data) => {
        this.cuotas.set(data);
      },
      error: (error) => {
        console.error('Error al cargar cuotas', error);
        this.mostrarError('No se pudieron cargar las cuotas');
      }
    });
  }

  generarCuotasDelMes(): void {
    this.cuotaService.generarCuotasDelMes(this.generarCuotasRequest).subscribe({
      next: () => {
        this.mostrarExito('Cuotas generadas exitosamente');

        this.generarCuotasRequest = {
          mes: new Date().getMonth() + 1,
          anio: new Date().getFullYear(),
          monto: 30
        };

        this.cargarCuotas();
      },
      error: (error) => {
        console.error('Error al generar cuotas del mes', error);
        this.mostrarError('No se pudieron generar las cuotas');
      }
    });
  }

  guardarOActualizarCuota(): void {
    const id = this.cuotaEditandoId();

    if (id !== null) {
      this.cuotaService.actualizarCuota(id, this.nuevaCuota).subscribe({
        next: () => {
          this.mostrarExito('Cuota actualizada correctamente');
          this.cancelarEdicionCuota();
          this.cargarCuotas();
        },
        error: (error) => {
          console.error('Error al actualizar cuota', error);
          this.mostrarError('No se pudo actualizar la cuota');
        }
      });
    } else {
      this.cuotaService.guardarCuota(this.nuevaCuota).subscribe({
        next: () => {
          this.mostrarExito('Cuota guardada correctamente');
          this.cancelarEdicionCuota();
          this.cargarCuotas();
        },
        error: (error) => {
          console.error('Error al guardar cuota', error);
          this.mostrarError('No se pudo guardar la cuota');
        }
      });
    }
  }

  eliminarCuota(id: number): void {
    const confirmado = confirm('¿Seguro que deseas eliminar esta cuota?');

    if (!confirmado) {
      return;
    }

    this.cuotaService.eliminarCuota(id).subscribe({
      next: () => {
        this.mostrarExito('Cuota eliminada correctamente');

        if (this.cuotaEditandoId() === id) {
          this.cancelarEdicionCuota();
        }

        this.cargarCuotas();
      },
      error: (error) => {
        console.error('Error al eliminar cuota', error);
        this.mostrarError('No se pudo eliminar la cuota');
      }
    });
  }

  seleccionarCuotaParaEditar(cuota: Cuota): void {
    this.nuevaCuota = {
      mes: cuota.mes,
      anio: cuota.anio,
      monto: cuota.monto,
      fechaVencimiento: cuota.fechaVencimiento,
      estadoCuota: 'PENDIENTE',
      viviendaId: cuota.vivienda.id
    };

    this.cuotaEditandoId.set(cuota.id);
    this.limpiarMensajes();
  }

  cancelarEdicionCuota(): void {
    this.nuevaCuota = {
      mes: 1,
      anio: new Date().getFullYear(),
      monto: 30,
      fechaVencimiento: '',
      estadoCuota: 'PENDIENTE',
      viviendaId: 0
    };

    this.cuotaEditandoId.set(null);
  }

  cargarPagos(): void {
    this.pagoService.listarPagos().subscribe({
      next: (data) => {
        this.pagos.set(data);
      },
      error: (error) => {
        console.error('Error al cargar pagos', error);
        this.mostrarError('No se pudieron cargar los pagos');
      }
    });
  }

  obtenerCuotasDisponiblesParaPago(): Cuota[] {
    return this.cuotas().filter(cuota => cuota.estado !== 'PAGADA');
  }

  actualizarMontoSegunCuota(): void {
    const cuotaSeleccionada = this.cuotas().find(
      cuota => cuota.id === this.nuevoPago.cuotaId
    );

    if (cuotaSeleccionada) {
      this.nuevoPago.montoPagado = cuotaSeleccionada.monto;
    }
  }

  guardarOActualizarPago(): void {
    const id = this.pagoEditandoId();

    if (id !== null) {
      this.pagoService.actualizarPago(id, this.nuevoPago).subscribe({
        next: () => {
          this.mostrarExito('Pago actualizado correctamente');
          this.cancelarEdicionPago();
          this.cargarPagos();
          this.cargarCuotas();
        },
        error: (error) => {
          console.error('Error al actualizar pago', error);
          this.mostrarError('No se pudo actualizar el pago');
        }
      });
    } else {
      this.pagoService.guardarPago(this.nuevoPago).subscribe({
        next: () => {
          this.mostrarExito('Pago guardado correctamente');
          this.cancelarEdicionPago();
          this.cargarPagos();
          this.cargarCuotas();
        },
        error: (error) => {
          console.error('Error al guardar pago', error);
          this.mostrarError('No se pudo guardar el pago');
        }
      });
    }
  }

  seleccionarPagoParaEditar(pago: Pago): void {
    this.nuevoPago = {
      fechaPago: pago.fechaPago,
      montoPagado: pago.montoPagado,
      metodoPago: pago.metodoPago,
      observacion: pago.observacion,
      cuotaId: pago.cuota.id
    };

    this.pagoEditandoId.set(pago.id);
    this.limpiarMensajes();
  }

  eliminarPago(id: number): void {
    const confirmado = confirm('¿Seguro que deseas eliminar este pago?');

    if (!confirmado) {
      return;
    }

    this.pagoService.eliminarPago(id).subscribe({
      next: () => {
        this.mostrarExito('Pago eliminado correctamente');

        if (this.pagoEditandoId() === id) {
          this.cancelarEdicionPago();
        }

        this.cargarPagos();
        this.cargarCuotas();
      },
      error: (error) => {
        console.error('Error al eliminar pago', error);
        this.mostrarError('No se pudo eliminar el pago');
      }
    });
  }

  cancelarEdicionPago(): void {
    this.nuevoPago = {
      fechaPago: new Date().toISOString().split('T')[0],
      montoPagado: 0,
      metodoPago: 'EFECTIVO',
      observacion: '',
      cuotaId: 0
    };

    this.pagoEditandoId.set(null);
  }

  consultarEstadoDeCuenta(): void {
    if (!this.viviendaSeleccionadaEstadoCuenta || this.viviendaSeleccionadaEstadoCuenta === 0) {
      this.estadoCuenta.set(null);
      return;
    }

    this.cuotaService.obtenerEstadoDeCuentaPorVivienda(this.viviendaSeleccionadaEstadoCuenta).subscribe({
      next: (data) => {
        this.mostrarExito('Estado de cuenta consultado correctamente');
        this.estadoCuenta.set(data);
      },
      error: (error) => {
        console.error('Error al consultar estado de cuenta', error);
        this.mostrarError('No se pudo consultar el estado de cuenta');
        this.estadoCuenta.set(null);
      }
    });
  }

  verRecibo(pago: Pago): void {
    this.pagoSeleccionadoRecibo.set(pago);
  }

  cerrarRecibo(): void {
    this.pagoSeleccionadoRecibo.set(null);
  }

  imprimirRecibo(): void {
    window.print();
  }
}
