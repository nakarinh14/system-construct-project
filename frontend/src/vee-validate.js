import { required, alpha_num, alpha, numeric } from 'vee-validate/dist/rules';
import { extend } from 'vee-validate';

extend('required', {
    ...required,
    message: 'This field is required'
});

extend('alpha_num', {
    ...alpha_num,
    message: 'No whitespaces or punctuations allowed'
});

extend('alpha', {
    ...alpha,
    message: 'Only alphabets characters are allowed'
})

extend('numeric', {
    ...numeric,
    message: 'Only numerical values allowed'
})