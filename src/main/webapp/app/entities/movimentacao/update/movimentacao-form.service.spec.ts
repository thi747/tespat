import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../movimentacao.test-samples';

import { MovimentacaoFormService } from './movimentacao-form.service';

describe('Movimentacao Form Service', () => {
  let service: MovimentacaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MovimentacaoFormService);
  });

  describe('Service methods', () => {
    describe('createMovimentacaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMovimentacaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            data: expect.any(Object),
            descricao: expect.any(Object),
            tipo: expect.any(Object),
            bem: expect.any(Object),
            pessoa: expect.any(Object),
          }),
        );
      });

      it('passing IMovimentacao should create a new form with FormGroup', () => {
        const formGroup = service.createMovimentacaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            data: expect.any(Object),
            descricao: expect.any(Object),
            tipo: expect.any(Object),
            bem: expect.any(Object),
            pessoa: expect.any(Object),
          }),
        );
      });
    });

    describe('getMovimentacao', () => {
      it('should return NewMovimentacao for default Movimentacao initial value', () => {
        const formGroup = service.createMovimentacaoFormGroup(sampleWithNewData);

        const movimentacao = service.getMovimentacao(formGroup) as any;

        expect(movimentacao).toMatchObject(sampleWithNewData);
      });

      it('should return NewMovimentacao for empty Movimentacao initial value', () => {
        const formGroup = service.createMovimentacaoFormGroup();

        const movimentacao = service.getMovimentacao(formGroup) as any;

        expect(movimentacao).toMatchObject({});
      });

      it('should return IMovimentacao', () => {
        const formGroup = service.createMovimentacaoFormGroup(sampleWithRequiredData);

        const movimentacao = service.getMovimentacao(formGroup) as any;

        expect(movimentacao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMovimentacao should not enable id FormControl', () => {
        const formGroup = service.createMovimentacaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMovimentacao should disable id FormControl', () => {
        const formGroup = service.createMovimentacaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
