import React, { useContext, useEffect, useState } from 'react'
import { Redirect } from 'react-router-dom';
import { Container, Table } from 'semantic-ui-react'
import { Product } from '../../types/product';
import AuthContext from '../context/AuthContext';
import { handleLogError } from '../misc/Helpers';
import { productApi } from '../misc/ProductApi';


const ProductPage = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true)
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(true)
  const authContext = useContext(AuthContext);

  useEffect(() => {
    setIsLoggedIn(authContext.userIsAuthenticated())
    handleGetProducts()
  }, [])

  const handleGetProducts = () => {
    setIsLoading(true)
    const getProducts = async () => {
      const user = authContext.getUser()
      const usersResponse = await productApi.getProducts(user)
      setProducts(usersResponse.data)
    }

    getProducts()
      .catch(error => handleLogError(error))
      .finally(() => setIsLoading(false))
  }

  const redirect = () => (
    <Redirect to='/' />
  )

  const main = () => {
    return !isLoading && (
      <Container>
        <Table singleLine>
          <Table.Header>
            <Table.Row>
              <Table.HeaderCell>Name</Table.HeaderCell>
              <Table.HeaderCell>Price</Table.HeaderCell>
              <Table.HeaderCell>Id</Table.HeaderCell>
            </Table.Row>
          </Table.Header>

          <Table.Body>
            {products.map(product => (
              <Table.Row key={product.id}>
                <Table.Cell>{product.name}</Table.Cell>
                <Table.Cell>{product.price}</Table.Cell>
                <Table.Cell>{product.id}</Table.Cell>
              </Table.Row>
            ))}
          </Table.Body>
        </Table>
      </Container>
    )
  }

  return <>
    {isLoggedIn ? main() : redirect()}
  </>
};

export default ProductPage