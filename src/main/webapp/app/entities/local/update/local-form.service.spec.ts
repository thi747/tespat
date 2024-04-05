import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../local.test-samples';

import { LocalFormService } from './local-form.service';

describe('Local Form Service', () => {
  let service: LocalFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LocalFormService);
  });

  describe('Service methods', () => {
    describe('createLocalFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLocalFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            nome: expect.any(Object),
            descricao: expect.any(Object),
            sala: expect.any(Object),
          }),
        );
      });

      it('passing ILocal should create a new form with FormGroup', () => {
        const formGroup = service.createLocalFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            nome: expect.any(Object),
            descricao: expect.any(Object),
            sala: expect.any(Object),
          }),
        );
      });
    });

    describe('getLocal', () => {
      it('should return NewLocal for default Local initial value', () => {
        const formGroup = service.createLocalFormGroup(sampleWithNewData);

        const local = service.getLocal(formGroup) as any;

        expect(local).toMatchObject(sampleWithNewData);
      });

      it('should return NewLocal for empty Local initial value', () => {
        const formGroup = service.createLocalFormGroup();

        const local = service.getLocal(formGroup) as any;

        expect(local).toMatchObject({});
      });

      it('should return ILocal', () => {
        const formGroup = service.createLocalFormGroup(sampleWithRequiredData);

        const local = service.getLocal(formGroup) as any;

        expect(local).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});
