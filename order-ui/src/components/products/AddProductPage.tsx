import React, { ChangeEvent, useContext, useState } from 'react'
import { Form, Grid, Message } from 'semantic-ui-react'
import { productApi } from '../../api/ProductApi';
import { CreateProduct } from '../../types/product';
import { handleLogError } from '../../util/Helpers';
import AuthContext from '../context/AuthContext';

const AddProductPage: React.FC = () => {
    const [name, setName] = useState<string>('');
    const [price, setPrice] = useState<string>('');
    const [success, setSuccess] = useState<boolean>(false)
    const [error, setError] = useState<boolean>(false)
    const authContext = useContext(AuthContext);


    const handlePriceChange = (event: ChangeEvent<HTMLInputElement>) => {
        setPrice(event.target.value)
    };

    const handleNameChange = (event: ChangeEvent<HTMLInputElement>) => {
        setName(event.target.value)
    };

    const handleSubmit = async () => {
        const product: CreateProduct = {
            name: name,
            price: Number(price)
        }

        const createProduct = async () => {
            await productApi.createProduct(authContext.getUser(), product)
            setName('')
            setPrice('')
            setSuccess(true)
            setError(false)
        }

        createProduct()
            .catch(error => {
                handleLogError(error)
                setSuccess(false)
                setError(true)
            })
    }

    return <>
        <Grid textAlign="center">
            <Grid.Column style={{ maxWidth: 450 }}>
                <Form onSubmit={handleSubmit} success={success} error={error}>
                    <Form.Group widths='equal'>
                        <Form.Field>
                            <Form.Input
                                size='huge'
                                label='Name'
                                required
                                placeholder='Name'
                                name='name'
                                value={name}
                                onChange={handleNameChange}
                            />
                        </Form.Field>
                        <Form.Field>
                            <Form.Input
                                type='number'
                                min='0'
                                size='huge'
                                label='Price'
                                required
                                placeholder='Price'
                                name='price'
                                value={price}
                                onChange={handlePriceChange}
                            />
                        </Form.Field>
                    </Form.Group>
                    <Form.Group>
                        <Form.Button size='huge' content='Submit' />
                    </Form.Group>
                    <Form.Group>
                        <Message
                            success
                            header='Product created'
                            content='You can add more if you need'
                        />
                        <Message
                            error
                            header='Failed to create product'
                            content='Please try again'
                        />
                    </Form.Group>

                </Form>
            </Grid.Column>
        </Grid>
    </>
}

export default AddProductPage
