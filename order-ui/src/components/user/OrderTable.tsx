import React, { ChangeEvent } from 'react'
import { Grid, Table, Header, Icon } from 'semantic-ui-react'
import { Order } from '../../types/order'
import OrderForm from '../misc/OrderForm'

interface OrderTableProps {
  orderDescription: string,
  orders: Order[] | null,
  handleInputChange: (event: ChangeEvent<HTMLInputElement>) => void,
  handleCreateOrder: () => void,
}

function OrderTable(props: OrderTableProps) {

  const getOrderList = () => {
    if (!props.orders || props.orders.length === 0) {
      return (
        <Table.Row key='no-order'>
          <Table.Cell collapsing textAlign='center' colSpan='3'>No order</Table.Cell>
        </Table.Row>
      )
    } else {
      return props.orders.map(order => {
        return (
          <Table.Row key={order.id}>
            <Table.Cell>{order.id}</Table.Cell>
            <Table.Cell>{order.createdAt}</Table.Cell>
            <Table.Cell>{order.description}</Table.Cell>
          </Table.Row>
        )
      })
    }
  }

  return (
    <>
      <Grid stackable divided>
        <Grid.Row columns='2'>
          <Grid.Column width='3'>
            <Header as='h2'>
              <Icon name='laptop' />
              <Header.Content>Orders</Header.Content>
            </Header>
          </Grid.Column>
          <Grid.Column>
            <OrderForm
              orderDescription={props.orderDescription}
              handleInputChange={props.handleInputChange}
              handleCreateOrder={props.handleCreateOrder}
            />
          </Grid.Column>
        </Grid.Row>
      </Grid>

      <Table compact striped>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell width={5}>ID</Table.HeaderCell>
            <Table.HeaderCell width={5}>Created At</Table.HeaderCell>
            <Table.HeaderCell width={6}>Description</Table.HeaderCell>
          </Table.Row>
        </Table.Header>
        <Table.Body>
          {getOrderList()}
        </Table.Body>
      </Table>
    </>
  )
}

export default OrderTable