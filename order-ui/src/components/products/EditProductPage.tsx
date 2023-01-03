import React, { ChangeEvent, useContext, useEffect, useState } from 'react'
import { Form, Grid, Message } from 'semantic-ui-react'
import { productApi } from '../../api/ProductApi';
import { CreateProduct } from '../../types/product';
import { handleLogError } from '../../util/Helpers';
import AuthContext from '../context/AuthContext';

const EditProductPage: React.FC = () => {
    const url = window.location.pathname
    const [name, setName] = useState<string>('');
    const [price, setPrice] = useState<string>('');
    const [success, setSuccess] = useState<boolean>(false)
    const [error, setError] = useState<boolean>(false)
    const authContext = useContext(AuthContext);

    useEffect(() => {
        const fetchData = async () => {
          const usersResponse = await productApi.getProduct(authContext.getUser(), url.split('/')[2])
          const priceWithComma = usersResponse.data.price.toString()
          const priceWithDot = priceWithComma.replace(/,/g, '.')
          console.log(priceWithComma)
          console.log(priceWithDot)
          setPrice(priceWithDot.toLocaleString('en-GB'))
          setName(usersResponse.data.name)
        }
    
        fetchData()
          .catch(error => handleLogError(error))
      }, [url])

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
            await productApi.editProduct(authContext.getUser(), url.split('/')[2], product)
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
                                step='.01'
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
                            header='Product updated'
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

export default EditProductPage
