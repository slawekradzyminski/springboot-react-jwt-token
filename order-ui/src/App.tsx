import React from 'react'
import { BrowserRouter as Router, Route } from 'react-router-dom'
import { AuthProvider } from './components/context/AuthContext'
import PrivateRoute from './router/PrivateRoute'
import Navbar from './components/misc/Navbar'
import Home from './components/home/Home'
import Login from './components/home/Login'
import Signup from './components/home/Signup'
import AdminPage from './components/admin/AdminPage'
import UserPage from './components/user/UserPage'
import ProductsPage from './components/products/ProductsPage'
import FileUpload from './components/file/FileUpload'
import AddProductPage from './components/products/AddProductPage'
import EditProductPage from './components/products/EditProductPage'

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <Navbar />
        <Route path='/' exact component={Home} />
        <Route path='/login' component={Login} />
        <Route path='/signup' component={Signup} />
        <PrivateRoute path='/adminpage' component={AdminPage} />
        <PrivateRoute path='/userpage' component={UserPage} />
        <PrivateRoute path='/products' component={ProductsPage}/>
        <PrivateRoute path='/add-product' component={AddProductPage}/>
        <PrivateRoute path='/upload' component={FileUpload}/>
        <PrivateRoute path="/product-edit/*" component={EditProductPage} />
      </Router>
    </AuthProvider>
  )
}

export default App
